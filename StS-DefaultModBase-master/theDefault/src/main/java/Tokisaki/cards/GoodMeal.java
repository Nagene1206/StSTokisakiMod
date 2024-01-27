package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.actions.ElegantShotAction;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CorrosionPower;
import Tokisaki.powers.ShadowPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Tokisaki.DefaultMod.makeCardPath;
// "How come this card extends CustomCard and not DynamicCard like all the rest?"
// Skip this question until you start figuring out the AbstractDefaultCard/AbstractDynamicCard and just extend DynamicCard
// for your own ones like all the other cards.

// Well every card, at the end of the day, extends CustomCard.
// Abstract Default Card extends CustomCard and builds up on it, adding a second magic number. Your card can extend it and
// bam - you can have a second magic number in that card (Learn Java inheritance if you want to know how that works).
// Abstract Dynamic Card builds up on Abstract Default Card even more and makes it so that you don't need to add
// the NAME and the DESCRIPTION into your card - it'll get it automatically. Of course, this functionality could have easily
// Been added to the default card rather than creating a new Dynamic one, but was done so to deliberately to showcase custom cards/inheritance a bit more.
public class GoodMeal extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(GoodMeal.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("GoodMeal.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name
    // and make sure it's in the correct folder. That's all.
    // There's makeCardPath, makeRelicPath, power, orb, event, etc..
    // The list of all of them can be found in the main DefaultMod.java file in the
    // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==


    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_COST = 2;


    public GoodMeal() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        damage = baseDamage = DAMAGE;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m!=null)
        {
            int dmg = 0;
            dmg += damage;

            if(p.hasPower(ShadowPower.POWER_ID))
            {
                dmg += p.getPower(ShadowPower.POWER_ID).amount;
                p.getPower(ShadowPower.POWER_ID).amount -= p.getPower(ShadowPower.POWER_ID).amount;;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, ShadowPower.POWER_ID));
            }
            if(m.hasPower(CorrosionPower.POWER_ID))
            {
                dmg += m.getPower(CorrosionPower.POWER_ID).amount;
                m.getPower(CorrosionPower.POWER_ID).amount -= m.getPower(CorrosionPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, m, CorrosionPower.POWER_ID));
            }

            addToBot((AbstractGameAction)new FeedAction(m, new DamageInfo(p,dmg,damageTypeForTurn),5));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
