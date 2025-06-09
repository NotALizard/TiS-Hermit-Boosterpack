package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.MementoCard;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.util.SpireHelp;

@SpirePatch2(
        clz = MementoCard.class,
        method = "use",
        requiredModId = "hermit",
        optional = true
)
public class MementoFriendlyFirePatch {
    public static void Postfix(MementoCard __instance, AbstractPlayer p, AbstractMonster m) {
        for(P2PPlayer np : SpireHelp.Multiplayer.Players.GetPlayers(true, true)) {
            np.addPower(new VulnerablePower(p, __instance.magicNumber, false));
            if(__instance.upgraded){
                //Doesn't seem possible to even upgrade this but adding this here for consistency with the base card's code.
                np.addPower(new WeakPower(p, __instance.magicNumber, false));
            }
        }
    }
}