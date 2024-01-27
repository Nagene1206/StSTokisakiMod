package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Tokisaki.DefaultMod.makeCardPath;

public class Kihihihi extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Kihihihi.class.getSimpleName());
    public static final String IMG = makeCardPath("Kihihihi.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int BLOCK = 8;

    // /STAT DECLARATION/


    public Kihihihi() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
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
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));

        AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;
        if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
            AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;

            if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
                AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;
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
