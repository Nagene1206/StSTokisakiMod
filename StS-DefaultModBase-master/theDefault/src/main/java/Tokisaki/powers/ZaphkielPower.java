package Tokisaki.powers;

import Tokisaki.DefaultMod;
import Tokisaki.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Tokisaki.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class ZaphkielPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ZaphkielPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Zaphkiel84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Zaphkiel32.png"));

    int counter = 0;

    public ZaphkielPower(final AbstractCreature owner, int amount) {
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
    public boolean canPlayCard(AbstractCard card) {
        if(counter >= 12)
        {
            card.cantUseMessage = DESCRIPTIONS[5];
            return false;
        }
        return true;
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        counter = 0;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(amount));
        counter++;
        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if(amount == 1) description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + (12 - counter) + DESCRIPTIONS[4];
        else description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + (12 - counter) + DESCRIPTIONS[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ZaphkielPower(owner, amount);
    }
}
