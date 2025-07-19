package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.ImpendingDoom;
import spireTogether.SpireTogetherMod;
import spireTogether.network.P2P.P2PManager;
import spireTogether.network.P2P.P2PPlayer;
import spireTogether.network.objects.settings.GameSettings;
import spireTogether.util.SpireHelp;

@SpirePatch2(
        clz = ImpendingDoom.class,
        method = "use",
        optional = true
)
public class ImpendingDoomFriendlyFirePatch {
    public static void Postfix(ImpendingDoom __instance, AbstractPlayer p, AbstractMonster m) {
        if (SpireTogetherMod.isConnected) {
            //Already hits allies if Friendly Fire is set to FULL so don't want to double it.
            //Don't want it to hit allies if Friendly Fire is set to NONE.
            //That just leaves TARGETING
            if (P2PManager.data.settings.friendlyFireType == GameSettings.FriendlyFireType.TARGETING) {
                for(P2PPlayer np : SpireHelp.Multiplayer.Players.GetPlayers(true, true)) {
                    np.damage(new DamageInfo(AbstractDungeon.player, __instance.baseDamage, DamageInfo.DamageType.THORNS));
                }
            }
        }
    }
}