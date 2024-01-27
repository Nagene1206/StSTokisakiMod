package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.CorrosionPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.Random;

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
public class BrokenCat extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BrokenCat.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("BrokenCat.png");
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
    private static final int UPGRADE_COST = 2;

    public BrokenCat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void triggerWhenDrawn() {
        int rnd_cost = AbstractDungeon.cardRandomRng.random(3);
        this.setCostForTurn(rnd_cost);
        this.isCostModified = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Random rnd = new Random();
        rnd.setSeed(System.currentTimeMillis());

        int value = rnd.nextInt(100);
        if(value < 5)   //5%
        {
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
        }
        else if(value < 15) //10%
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new StrengthPower(m,3)));
        }
        else if(value < 35)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p, 30,damageTypeForTurn),AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        else if(value < 55)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new PoisonPower(m,p,10)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new CorrosionPower(m,p,10)));
        }
        else if(value < 65)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllPowersAction(m,false));
        }
        else if(value < 85)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new VulnerablePower(m, 5, false)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new WeakPower(m, 5, false)));
        }
        else
        {
            m.currentHealth = m.maxHealth - m.currentHealth;
            m.healthBarUpdatedEvent();
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
