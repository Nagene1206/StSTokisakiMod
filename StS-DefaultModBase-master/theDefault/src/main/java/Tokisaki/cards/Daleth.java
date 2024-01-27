package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.actions.GunShotAction;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.ShadowPower;
import Tokisaki.relics.CastleThatEatTime;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import sun.security.action.GetIntegerAction;

import java.util.AbstractMap;
import java.util.ArrayList;

import static Tokisaki.DefaultMod.makeCardPath;

public class Daleth extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Daleth.class.getSimpleName());
    public static final String IMG = makeCardPath("Daleth.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    // /STAT DECLARATION/


    public Daleth() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.selfRetain = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GunShotAction());
        AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth/2;
        AbstractDungeon.player.healthBarUpdatedEvent();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
