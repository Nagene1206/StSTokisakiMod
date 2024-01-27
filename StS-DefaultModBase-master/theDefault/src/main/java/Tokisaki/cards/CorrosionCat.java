package Tokisaki.cards;

import Tokisaki.DefaultMod;
import Tokisaki.characters.TheDefault;
import Tokisaki.powers.CatPower;
import Tokisaki.powers.CorrosionPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Tokisaki.DefaultMod.makeCardPath;

public class CorrosionCat extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(CorrosionCat.class.getSimpleName());
    public static final String IMG = makeCardPath("CorrosionCat.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    // /STAT DECLARATION/


    public CorrosionCat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cnt = 0;
        if(p.hasPower(CatPower.POWER_ID)) cnt = p.getPower(CatPower.POWER_ID).amount;
        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new CorrosionPower(mo, p, cnt)));
        }
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
