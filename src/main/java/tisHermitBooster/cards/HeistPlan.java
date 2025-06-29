package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.characters.hermit;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class HeistPlan extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(HeistPlan.class.getSimpleName());
    public static final String IMG = cardPath("skill/HeistPlan.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 1;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public HeistPlan() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.cardsToPreview = new Diversion();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInDrawPileAction(new Diversion(), 1, true, true));

        for(P2PPlayer player : this.getPlayers(true, true)) {
            player.addCard(NetworkCard.Generate(new Diversion()), CardGroup.CardGroupType.DRAW_PILE);
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.isInnate = true;
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }
}