package Tokisaki.relics;

import Tokisaki.DefaultMod;
import Tokisaki.powers.ShadowPower;
import Tokisaki.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.devcommands.relic.RelicRemove;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import javafx.scene.effect.Shadow;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static Tokisaki.DefaultMod.makeRelicOutlinePath;
import static Tokisaki.DefaultMod.makeRelicPath;

public class CastleThatBingeTime extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CastleThatBingeTime");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CastleThatBingeTime.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CastleThatBingeTime.png"));

    public CastleThatBingeTime() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        if(AbstractDungeon.player.hasPower(ShadowPower.POWER_ID))
        {
            int num = AbstractDungeon.player.getPower(ShadowPower.POWER_ID).amount;
            if(num >= 3) num = 3;
            AbstractDungeon.actionManager.addToBottom(new HealAction(p,p,num));
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
