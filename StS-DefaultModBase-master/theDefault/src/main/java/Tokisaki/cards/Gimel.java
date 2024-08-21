package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import basemod.devcommands.hand.Hand;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

import java.awt.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.function.Consumer;

import static Tokisaki.DefaultMod.makeCardPath;

public class Gimel extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Gimel.class.getSimpleName());
    public static final String IMG = makeCardPath("Gimel.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;

    // /STAT DECLARATION/


    public Gimel() {
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
        if(!upgraded)
        {
            if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount >= 3)
            {
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
                AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 3;
            }
            else
            {
                AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(false));
                AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;
            }
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ArmamentsAction(true));
            AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 1;

            if(AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount >= 2)
            {
                ArrayList<AbstractCard> c = AbstractDungeon.player.drawPile.group;
                for(AbstractCard i : c)
                    AbstractDungeon.actionManager.addToBottom(new UpgradeSpecificCardAction(i));
                AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount -= 2;
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
