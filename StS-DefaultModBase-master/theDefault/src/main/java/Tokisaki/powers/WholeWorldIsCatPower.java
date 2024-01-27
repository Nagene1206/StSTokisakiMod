package Tokisaki.powers;

import Tokisaki.DefaultMod;
import Tokisaki.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Tokisaki.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class WholeWorldIsCatPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("WholeWorldIsCatPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("WholeWorldIsCatPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("WholeWorldIsCatPower32.png"));

    public WholeWorldIsCatPower(final AbstractCreature owner, int amount) {
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
    public void atStartOfTurn() {
        for(int i=0;i<amount;i++)
        {
            int roll = AbstractDungeon.cardRandomRng.random(99);
            AbstractCard.CardRarity cardRarity;
            if (roll < 55) {
                cardRarity = AbstractCard.CardRarity.COMMON;
            } else if (roll < 85) {
                cardRarity = AbstractCard.CardRarity.UNCOMMON;
            } else {
                cardRarity = AbstractCard.CardRarity.RARE;
            }
            AbstractCard c = CardLibrary.getAnyColorCard(cardRarity).makeCopy();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if(amount == 1) description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new WholeWorldIsCatPower(owner, amount);
    }
}
