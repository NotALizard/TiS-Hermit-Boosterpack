package tisHermitBooster.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hermit.cards.Defend_Hermit;
import spireTogether.SpireTogetherMod;
import spireTogether.monsters.CharacterEntity;
import spireTogether.other.MPSkillsPatches;

public class StandaloneMPDefendPatches {
    @SpirePatch2(
            clz = MPSkillsPatches.class,
            method = "IsUpgradedMPDefend",
            requiredModId = "Hermit",
            optional = true
    )
    public static class Inserter {
        public static boolean Postfix(AbstractCard o, boolean __result) {
            if (!__result && o.upgraded) {

                if (o instanceof Defend_Hermit) {
                    return true;
                }

            }

            return __result;
        }
    }

    @SpirePatch(
            clz = Defend_Hermit.class,
            method = "use",
            requiredModId = "Hermit",
            optional = true
    )
    public static class StandaloneHermitDefendUsePatcher {
        public static SpireReturn Prefix(Defend_Hermit __instance, AbstractPlayer p, AbstractMonster m) {
            if (SpireTogetherMod.isConnected && m instanceof CharacterEntity) {
                m.addBlock(__instance.block);
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}
