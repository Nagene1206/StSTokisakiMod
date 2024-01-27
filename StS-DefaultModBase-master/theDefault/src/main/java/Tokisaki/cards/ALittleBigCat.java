package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.ShadowPower;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

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
public class ALittleBigCat extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ALittleBigCat.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("ALittleBigCat.png");
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
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;

    public ALittleBigCat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        isMultiDamage = true;
        this.exhaust = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasPower(CatPower.POWER_ID))
        {
            return super.canUse(p,m);
        }
        return false;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int num = p.getPower(CatPower.POWER_ID).amount;
        for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if(!mo.isDead) {
                if (Settings.FAST_MODE) {
                    addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new BiteEffect(mo.hb.cX, mo.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR
                            .cpy()), 0.1F));
                } else {
                    addToBot((AbstractGameAction) new VFXAction((AbstractGameEffect) new BiteEffect(mo.hb.cX, mo.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR
                            .cpy()), 0.3F));
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p,num,damageTypeForTurn,AbstractGameAction.AttackEffect.NONE));
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
                    new WeakPower(mo, num, false), num));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
