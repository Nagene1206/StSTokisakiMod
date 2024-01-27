package Tokisaki.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class ElegantShotAction extends AbstractGameAction {
    private int drawAmount;

    private DamageInfo info;

    private static final float DURATION = 0.1F;
    private boolean u;

    public ElegantShotAction(AbstractCreature target, DamageInfo info, int draw, boolean u) {
        this.info = info;
        setValues(target, info);
        this.drawAmount = draw;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.u = u;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractDungeon.player.draw(drawAmount);
                if(u) AbstractDungeon.player.gainEnergy(1);
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }
}
