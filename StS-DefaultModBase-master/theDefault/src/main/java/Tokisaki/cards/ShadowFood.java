package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;

import static Tokisaki.DefaultMod.makeCardPath;

public class ShadowFood extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ShadowFood.class.getSimpleName());
    public static final String IMG = makeCardPath("ShadowFood.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;

    // /STAT DECLARATION/


    public ShadowFood() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasPower(ShadowPower.POWER_ID))
        {
            if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 2)
                return super.canUse(p,m);
        }
        return false;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
        AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 3;

        if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount == 0)
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, ShadowPower.POWER_ID));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
