package Tokisaki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Iterator;

public class CrookedAction extends AbstractGameAction {
    private boolean freeToPlayOnce;

    private int damage;

    private AbstractPlayer p;

    private AbstractMonster m;

    private DamageInfo.DamageType damageTypeForTurn;

    private int energyOnUse;
    private boolean upgrade;

    public CrookedAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, boolean upgrade) {
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.upgrade = upgrade;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (this.upgrade) {
            if (effect >= 0) {
                for (int i = 0; i < effect+2; i++)
                {
                    addToBot(new GunShotAction());
                    addToBot((AbstractGameAction)new DamageAction((AbstractCreature)this.m, new DamageInfo((AbstractCreature)this.p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }
                addToBot(new MakeTempCardInDiscardAction(new Wound(),effect));
                if (!this.freeToPlayOnce)
                    this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        else
        {
            if (effect > 0) {
                for (int i = 0; i < effect; i++)
                {
                    addToBot(new GunShotAction());
                    addToBot((AbstractGameAction)new DamageAction((AbstractCreature)this.m, new DamageInfo((AbstractCreature)this.p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }
                addToBot(new MakeTempCardInDiscardAction(new Wound(),effect));
                if (!this.freeToPlayOnce)
                    this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
