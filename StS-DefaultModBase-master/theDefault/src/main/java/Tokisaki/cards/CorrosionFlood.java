package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatHatePower;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.CorrosionPower;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Tokisaki.DefaultMod.makeCardPath;

public class CorrosionFlood extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CorrosionFlood.class.getSimpleName());
    public static final String IMG = makeCardPath("CorrosionFlood.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    // /STAT DECLARATION/


    public CorrosionFlood() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.hasPower(ShadowPower.POWER_ID))
        {
            return super.canUse(p,m);
        }
        return false;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cnt = AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount;
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, ShadowPower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new CorrosionPower(m,m,cnt),cnt));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
