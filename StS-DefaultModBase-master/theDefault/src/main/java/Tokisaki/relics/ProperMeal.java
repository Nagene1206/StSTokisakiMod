package Tokisaki.relics;

import Tokisaki.DefaultMod;
import Tokisaki.powers.ShadowPower;
import Tokisaki.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Tokisaki.DefaultMod.makeRelicOutlinePath;
import static Tokisaki.DefaultMod.makeRelicPath;

public class ProperMeal extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("ProperMeal");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ProperMeal.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ProperMeal.png"));

    public ProperMeal() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
