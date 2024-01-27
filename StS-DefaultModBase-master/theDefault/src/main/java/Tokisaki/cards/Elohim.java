package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import static Tokisaki.DefaultMod.makeCardPath;

public class Elohim extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Elohim.class.getSimpleName());
    public static final String IMG = makeCardPath("Elohim.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    // /STAT DECLARATION/


    public Elohim() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new PlatedArmorPower(p, 6),6));

        AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 3;
        if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 1)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new ArtifactPower(p, 1)));
            AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 2;

            if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 1)
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new BufferPower(p, 1)));
                AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 2;

                if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 1)
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new IntangiblePlayerPower(p, 1),1));
                    AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 2;
                }
            }
        }

        if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount == 0)
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, ShadowPower.POWER_ID));
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
