package Tokisaki.potions;

import Tokisaki.powers.CorrosionPower;
import Tokisaki.powers.ShadowPower;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class CorrosionPotion extends CustomPotion {

    public static final String POTION_ID = Tokisaki.DefaultMod.makeID("CorrosionPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public CorrosionPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.POISON);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = true;
        targetRequired = true;
        
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        if(Settings.language == Settings.GameLanguage.ZHS)
            tips.add(new PowerTip("侵蚀","每次开始旋转都会让你失去数值般的体力。 如果敌人因侵蚀而死亡，最大体力增加2。"));
        else if(Settings.language == Settings.GameLanguage.KOR)
            tips.add(new PowerTip("침식","턴이 시작할 때마다 수치만큼 체력을 잃게합니다. 만약 침식으로 적이 사망하면 최대 체력이 2 증가합니다."));
        else
            tips.add(new PowerTip("corrosion","At the start of each turn, you lose HP equal to this number. If an enemy dies due to Corrosion, maximum health increases by 2."));
    }
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */

    @Override
    public void use(AbstractCreature target) {
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(target,AbstractDungeon.player, new CorrosionPower(target,AbstractDungeon.player,this.potency), this.potency));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new CorrosionPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 5;
    }
}
