package Tokisaki.powers;

import Tokisaki.DefaultMod;
import Tokisaki.cards.Strike_T;
import Tokisaki.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Tokisaki.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class CreateBulletPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("CreateBulletPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("BulletCreate84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("BulletCreate32.png"));

    private boolean upgrade;

    public CreateBulletPower(final AbstractCreature owner, int amount, boolean upgrade) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.upgrade = upgrade;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        for(int i=0;i<amount;i++)
        {
            AbstractCard c = new Strike_T().makeCopy();
            c.cost = 0;
            c.costForTurn = 0;
            c.isCostModified = true;
            addToBot(new MakeTempCardInDrawPileAction(c,1,true,true));
            if(upgrade) addToBot(new MakeTempCardInDiscardAction(c,1));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if(!upgrade) description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CreateBulletPower(owner, amount, upgrade);
    }
}
