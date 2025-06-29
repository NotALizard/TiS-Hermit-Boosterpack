package tisHermitBooster.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import hermit.characters.hermit;
import hermit.powers.Rugged;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.items.NetworkCard;

import java.util.ArrayList;
import java.util.Random;

import static tisHermitBooster.tisHermitBoosterMod.cardPath;
import static tisHermitBooster.tisHermitBoosterMod.makeID;

public class Scapegoat extends AbstractHermitMultiplayerCard {
    public static final String ID = makeID(Scapegoat.class.getSimpleName());
    public static final String IMG = cardPath("skill/Scapegoat.png");
    private static final CardRarity RARITY;
    private static final CardTarget TARGET;
    private static final CardType TYPE;
    public static final CardColor COLOR;
    private static final int COST = 2;

    static {
        RARITY = CardRarity.UNCOMMON;
        TARGET = CardTarget.SELF;
        TYPE = CardType.SKILL;
        COLOR = hermit.Enums.COLOR_YELLOW;
    }

    public Scapegoat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(P2PPlayer np : this.getPlayers(true, false)) {
            //List all curses in the player's draw pile
            ArrayList<AbstractCard> drawCurses = new ArrayList<>();
            for(NetworkCard nc : np.drawPile) {
                AbstractCard c = nc.ToStandard();
                if(c.type == CardType.CURSE){
                    drawCurses.add(c);
                }
            }

            //Transfer a random one if there are any
            if(!drawCurses.isEmpty()){
                int randIdx = (new Random()).nextInt(drawCurses.size());
                AbstractCard randCurse = drawCurses.get(randIdx);
                Scapegoat.this.addToBot(new VFXAction(new ShowCardAndAddToHandEffect(randCurse)));
                Scapegoat.this.addToBot(new ApplyPowerAction(p, p, new Rugged(p, 1), 1));
                for(NetworkCard nc : np.drawPile){
                    if(nc.uniqueID.equals(randCurse.uuid)){
                        np.removeCard(nc, CardGroup.CardGroupType.DRAW_PILE);
                    }
                }
            }
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