package Tokisaki.relics;

import Tokisaki.DefaultMod;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.ShadowPower;
import Tokisaki.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Tokisaki.DefaultMod.makeRelicOutlinePath;
import static Tokisaki.DefaultMod.makeRelicPath;

public class CatToy extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CatToy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CatToy.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CatToy.png"));

    public CatToy() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.FLAT);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    @Override
    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.player.addPower(new CatPower(p, p, 1));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
