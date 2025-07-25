package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.MementoCard;
import spireTogether.SpireTogetherMod;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.settings.GameSettings;
import spireTogether.util.SpireHelp;

@SpirePatch2(
        clz = MementoCard.class,
        method = "use",
        optional = true
)
public class MementoFriendlyFirePatch {
    public static void Postfix(MementoCard __instance, AbstractPlayer p, AbstractMonster m) {
        if (SpireTogetherMod.isConnected) {
            if (P2PManager.data.settings.friendlyFireType != GameSettings.FriendlyFireType.NONE) {
                for(P2PPlayer np : SpireHelp.Multiplayer.Players.GetPlayers(true, true)) {
                    np.addPower(new VulnerablePower(p, __instance.magicNumber, false));
                    if(__instance.upgraded){
                        //Doesn't seem possible to even upgrade this but adding this here for consistency with the base card's code.
                        np.addPower(new WeakPower(p, __instance.magicNumber, false));
                    }
                }
            }
        }
    }
}