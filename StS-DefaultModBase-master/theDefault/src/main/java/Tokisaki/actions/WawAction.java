package Tokisaki.actions;

import Tokisaki.cards.Waw;
import Tokisaki.powers.ShadowPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Iterator;

public class WawAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private boolean upgraded;

    private ArrayList<AbstractCard> waw = new ArrayList<>();

    public WawAction(boolean upgraded) {
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.exhaustPile.size() == 1) {
                if ((this.p.exhaustPile.group.get(0)).cardID.equals(Waw.ID)) {
                    this.isDone = true;
                    return;
                }
                AbstractCard abstractCard = this.p.exhaustPile.getTopCard();
                abstractCard.unfadeOut();
                this.p.hand.addToHand(abstractCard);
                abstractCard.modifyCostForCombat(-9);
                this.p.exhaustPile.removeCard(abstractCard);
                if (this.upgraded && abstractCard.canUpgrade())
                    abstractCard.upgrade();
                abstractCard.unhover();
                abstractCard.fadingOut = false;
                this.isDone = true;
                return;
            }
            for (AbstractCard abstractCard : this.p.exhaustPile.group) {
                abstractCard.stopGlowing();
                abstractCard.unhover();
                abstractCard.unfadeOut();
            }
            for (Iterator<AbstractCard> c = this.p.exhaustPile.group.iterator(); c.hasNext(); ) {
                AbstractCard derp = c.next();
                if (derp.cardID.equals(Waw.ID)) {
                    c.remove();
                    this.waw.add(derp);
                }
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.p.exhaustPile.group.addAll(this.waw);
                this.waw.clear();
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);   //요망
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.hand.addToHand(c);
                c.modifyCostForCombat(-9);
                this.p.exhaustPile.removeCard(c);
                if (this.upgraded && c.canUpgrade())
                    c.upgrade();
                c.unhover();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.p.exhaustPile.group.addAll(this.waw);
            this.waw.clear();
            for (AbstractCard c : this.p.exhaustPile.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            }
        }
        tickDuration();
    }
}
