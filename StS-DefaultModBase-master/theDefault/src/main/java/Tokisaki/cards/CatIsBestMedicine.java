package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.CorrosionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static Tokisaki.DefaultMod.makeCardPath;

public class CatIsBestMedicine extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CatIsBestMedicine.class.getSimpleName());
    public static final String IMG = makeCardPath("CatIsBestMedicine.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    // /STAT DECLARATION/


    public CatIsBestMedicine() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractCard c:AbstractDungeon.player.drawPile.group)
        {
            if(c.type == CardType.CURSE || c.type == CardType.STATUS)
            {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.drawPile));
            }
        }
        for(AbstractCard c:AbstractDungeon.player.hand.group)
        {
            if(c.type == CardType.CURSE || c.type == CardType.STATUS)
            {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.hand));
            }
        }
        for(AbstractCard c:AbstractDungeon.player.discardPile.group)
        {
            if(c.type == CardType.CURSE || c.type == CardType.STATUS)
            {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.discardPile));
            }
        }

        AbstractDungeon.actionManager.addToBottom(new RemoveAllPowersAction(p, true));
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
