package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Tokisaki.DefaultMod.makeCardPath;

public class Araara extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Araara.class.getSimpleName());
    public static final String IMG = makeCardPath("Araara.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;

    // /STAT DECLARATION/


    public Araara() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
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
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));

        AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;
        if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
            if(upgraded) AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));

            AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;

            if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 2));
                if(upgraded) AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));

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
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
