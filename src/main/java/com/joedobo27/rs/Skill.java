package com.joedobo27.rs;

import java.util.Arrays;
import java.util.Objects;

public enum Skill {
    NONE,
    MIND,
    BODY,
    SOUL,
    BODY_CONTROL,
    BODY_STAMINA,
    BODY_STRENGTH,
    MIND_LOGIC,
    MIND_SPEED,
    SOUL_DEPTH,
    SOUL_STRENGTH,
    SWORDS,
    AXES,
    KNIVES,
    MAULS,
    CLUBS,
    HAMMERS,
    ARCHERY,
    POLEARMS,
    TAILORING,
    COOKING,
    SMITHING,
    WEAPON_SMITHING,
    ARMOUR_SMITHING,
    MISCELLANEOUS_ITEMS,
    SHIELDS,
    ALCHEMY,
    NATURE,
    TOYS,
    FIGHTING,
    HEALING,
    RELIGION,
    THIEVERY,
    WAR_MACHINES,
    FARMING,
    PAPYRUSMAKING,
    THATCHING,
    GARDENING,
    MEDITATING,
    FORESTRY,
    RAKE,
    SCYTHE,
    SICKLE,
    SMALL_AXE,
    MINING,
    DIGGING,
    PICKAXE,
    SHOVEL,
    POTTERY,
    ROPEMAKING,
    WOODCUTTING,
    HATCHET,
    LEATHERWORKING,
    CLOTH_TAILORING,
    MASONRY,
    BLADES_SMITHING,
    WEAPON_HEADS_SMITHING,
    CHAIN_ARMOUR_SMITHING,
    PLATE_ARMOUR_SMITHING,
    SHIELD_SMITHING,
    BLACKSMITHING,
    DAIRY_FOOD_MAKING,
    HOT_FOOD_COOKING,
    BAKING,
    BEVERAGES,
    LONGSWORD,
    LARGE_MAUL,
    MEDIUM_MAUL,
    SMALL_MAUL,
    WARHAMMER,
    LONG_SPEAR,
    HALBERD,
    STAFF,
    CARVING_KNIFE,
    BUTCHERING_KNIFE,
    STONE_CHISEL,
    HUGE_CLUB,
    SAW,
    BUTCHERING,
    CARPENTRY,
    FIREMAKING,
    TRACKING,
    SMALL_WOODEN_SHIELD,
    MEDIUM_WOODEN_SHIELD,
    LARGE_WOODEN_SHIELD,
    SMALL_METAL_SHIELD,
    LARGE_METAL_SHIELD,
    MEDIUM_METAL_SHIELD,
    LARGE_AXE,
    HUGE_AXE,
    SHORTSWORD,
    TWO_HANDED_SWORD,
    HAMMER,
    PAVING,
    PROSPECTING,
    FISHING,
    LOCKSMITHING,
    REPAIRING,
    COAL0MAKING,
    MILLING,
    METALLURGY,
    NATURAL_SUBSTANCES,
    JEWELRY_SMITHING,
    FINE_CARPENTRY,
    BOWYERY,
    FLETCHING,
    YOYO,
    PUPPETEERING,
    TOY_MAKING,
    WEAPONLESS_FIGHTING,
    AGGRESSIVE_FIGHTING,
    DEFENSIVE_FIGHTING,
    NORMAL_FIGHTING,
    FIRST_AID,
    TAUNTING,
    SHIELD_BASHING,
    MILKING,
    PREACHING,
    PRAYER,
    CHANNELING,
    EXORCISM,
    ARTIFACTS,
    FORAGING,
    BOTANIZING,
    CLIMBING,
    STONE_CUTTING,
    LOCK_PICKING,
    STEALING,
    TRAPS,
    CATAPULTS,
    ANIMAL_TAMING,
    ANIMAL_HUSBANDRY,
    SHORT_BOW,
    LONG_BOW,
    MEDIUM_BOW,
    SHIP_BUILDING,
    BALLISTAE,
    TREBUCHETS,
    TURRETS;

    String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }

    static Skill getSkillFromString(String string) {
        return Arrays.stream(values())
                .filter(skill -> Objects.equals(skill.getName(), string))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}