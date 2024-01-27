package Tokisaki.powers;

import Tokisaki.DefaultMod;
import Tokisaki.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Tokisaki.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class CorrosionShieldPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("CorrosionShieldPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CorrosionShield84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CorrosionShield32.png"));

    public CorrosionShieldPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        int num = 0;
        for(AbstractMonster mo:AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if(mo.hasPower(CorrosionPower.POWER_ID))
                num += mo.getPower(CorrosionPower.POWER_ID).amount;
        }

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, num));
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CorrosionShieldPower(owner);
    }
}
