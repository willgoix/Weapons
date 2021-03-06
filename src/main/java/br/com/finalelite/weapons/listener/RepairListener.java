package br.com.finalelite.weapons.listener;

import br.com.finalelite.weapons.object.WeaponItem;
import br.com.finalelite.weapons.util.Text;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Willian Gois (github/willgoix)
 */
public class RepairListener implements Listener {

    private static final Integer REPAIR_PERCENTAGE = 25;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCursor() == null) return;

        WeaponItem weaponItem = WeaponItem.of(event.getCurrentItem());

        if (weaponItem != null && event.getCursor().getType() == Material.EMERALD) {
            ItemMeta meta = weaponItem.getItem().getItemMeta();
            Damageable damageable = (Damageable) meta;

            if (damageable.hasDamage()) {
                if (player.getLevel() < 5) {
                    player.sendMessage(Text.translate("&cVocê precisa de 5 níveis de experiência para reparar essa arma!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10f, 5f);
                    return;
                }
                player.setLevel(player.getLevel() - 5);

                int damageRepair = (weaponItem.getItem().getMaxItemUseDuration() / 100) * REPAIR_PERCENTAGE;
                damageable.setDamage(Math.max(damageable.getDamage() - damageRepair, 0));

                if (player.getItemOnCursor().getAmount() > 1) {
                    player.getItemOnCursor().setAmount(player.getItemOnCursor().getAmount() - 1);
                } else {
                    player.setItemOnCursor(null);
                }

                weaponItem.getItem().setItemMeta(meta);
                event.setCurrentItem(weaponItem.getItem());

                event.setCancelled(true);
                event.setResult(Result.DENY);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 10f, 5f);
            } else {
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10f, 5f);
            }
        }
    }
}
