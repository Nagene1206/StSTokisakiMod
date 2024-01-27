package Tokisaki.relics;

import Tokisaki.powers.ShadowPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import Tokisaki.DefaultMod;
import Tokisaki.util.TextureLoader;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import static Tokisaki.DefaultMod.makeRelicOutlinePath;
import static Tokisaki.DefaultMod.makeRelicPath;

public class CastleThatEatTime extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID("CastleThatEatTime");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CastleThatEatTime.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CastleThatEatTime.png"));

    public CastleThatEatTime() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        AbstractMonster mo;
        if(!AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty()) {
            mo = AbstractDungeon.getRandomMonster();
            if (mo.hasPower(TimeWarpPower.POWER_ID))
            {
                if(Settings.language == Settings.GameLanguage.KOR)
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F,
                        "기묘한 공간이라 시간이 제 맘대로 흘러가지 않는군요.", true));
                else
                    AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F,
                            "It's a strange space, so time doesn't flow as I want.", true));
            }
        }
    }

    @Override
    public void atTurnStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.player.addPower(new ShadowPower(p, p, 1));
    }

    /*
    // Gain 1 energy on equip.
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }
    */

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
