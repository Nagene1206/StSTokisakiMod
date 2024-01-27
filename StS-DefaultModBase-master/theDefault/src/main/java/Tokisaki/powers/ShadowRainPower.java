package Tokisaki.powers;

import Tokisaki.DefaultMod;
import Tokisaki.actions.CatHateThisAction;
import Tokisaki.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javafx.scene.effect.Shadow;

import static Tokisaki.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class ShadowRainPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ShadowRainPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ShadowRain84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ShadowRain32.png"));

    public ShadowRainPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(AbstractDungeon.player.hasPower(ShadowPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(AbstractDungeon.player,
                    AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS,
                    AbstractGameAction.AttackEffect.NONE));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShadowRainPower(owner, amount);
    }
}
