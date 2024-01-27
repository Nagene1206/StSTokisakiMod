package Tokisaki.actions;

import Tokisaki.relics.CastleThatEatTime;
import Tokisaki.relics.ProperMeal;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class CorrosionAction extends AbstractGameAction {

    public CorrosionAction(AbstractCreature target, AbstractCreature source, int amount, AttackEffect effect) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33F;
    }

    public void update() {
        if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.33F && this.target.currentHealth > 0)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        tickDuration();
        if (this.isDone) {
            if (this.target.currentHealth > 0) {
                this.target.tint.color = Color.BLACK.cpy();
                this.target.tint.changeColor(Color.WHITE.cpy());
                this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS));
                if ((((AbstractCreature)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead &&
                !this.target.isPlayer) {
                    AbstractDungeon.player.increaseMaxHp(2,true);
                    if(AbstractDungeon.player.hasRelic(ProperMeal.ID))
                    {
                        AbstractDungeon.player.increaseMaxHp(5,true);
                        AbstractRelic r = AbstractDungeon.player.getRelic(ProperMeal.ID);
                        r.flash();
                    }
                }
            }
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
            addToTop((AbstractGameAction)new WaitAction(0.1F));
        }
    }
}
