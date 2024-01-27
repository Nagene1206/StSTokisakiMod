package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import java.util.Random;

import static Tokisaki.DefaultMod.makeCardPath;

public class ActinidiaBranch extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ActinidiaBranch.class.getSimpleName());
    public static final String IMG = makeCardPath("ActinidiaBranch.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;

    // /STAT DECLARATION/


    public ActinidiaBranch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isMultiDamage = true;
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
        if(upgraded)
            AbstractDungeon.player.gainGold(AbstractDungeon.player.getPower(CatPower.POWER_ID).amount*2);
        else
        {
            AbstractDungeon.player.getPower(CatPower.POWER_ID).amount /= 2;
            AbstractDungeon.player.gainGold(AbstractDungeon.player.getPower(CatPower.POWER_ID).amount*2);
        }

        if(AbstractDungeon.player.getPower(CatPower.POWER_ID).amount == 0)
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, CatPower.POWER_ID));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
