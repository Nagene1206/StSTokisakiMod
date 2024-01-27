package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Tokisaki.DefaultMod.makeCardPath;

public class BloodAndTime extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BloodAndTime.class.getSimpleName());
    public static final String IMG = makeCardPath("BloodAndTime.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 9;
    private static final int BLOCK = 6;
    private static final int UPGRADE_AMOUNT = 3;

    // /STAT DECLARATION/


    public BloodAndTime() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p,p,3));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ShadowPower(p,p,magicNumber)));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_AMOUNT);
            upgradeMagicNumber(UPGRADE_AMOUNT);
            initializeDescription();
        }
    }
}
