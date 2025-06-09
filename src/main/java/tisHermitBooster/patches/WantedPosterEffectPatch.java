package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spireTogether.network.P2P.P2PPlayer;
import tisHermitBooster.relics.WantedPoster;

@SpirePatch2(
        clz = P2PPlayer.class,
        method = "addPower",
        requiredModId = "spireTogether",
        optional = true
)
public class WantedPosterEffectPatch {
    public static void Postfix(P2PPlayer __instance, AbstractPower powerToApply) {
        AbstractPlayer p = AbstractDungeon.player;
        if (powerToApply.type == AbstractPower.PowerType.DEBUFF && p.hasRelic(WantedPoster.ID)) {
            WantedPoster wp = (WantedPoster)p.getRelic(WantedPoster.ID);
            wp.triggerRelic(__instance);
        }
    }
}