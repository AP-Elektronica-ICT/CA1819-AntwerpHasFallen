using BusinessLayer;
using DataLayer.Model.InventoryModel;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AntwerpHasFallen.Controllers
{
    [Produces("application/json")]
    [Route("api/inventory")]
    public class InventoryController : Controller
    {
        private readonly InventoryService inventoryService;

        public InventoryController(InventoryService inventoryService)
        {
            this.inventoryService = inventoryService;
        }

        public IActionResult getAllInventoryItems()
        {
            List<InventoryItem> inventoryItems = new List<InventoryItem>();
            inventoryService.GetShopItems().ForEach(item =>
                inventoryItems.Add(item));
            inventoryService.GetIngredients().ForEach(item =>
                inventoryItems.Add(item));

            return Ok(inventoryItems);
        }

        [Route("{id}")]
        [HttpGet]
        public IActionResult getInventory(int id)
        {
            if (inventoryService.getInventory(id) != null)
                return Ok(inventoryService.getInventory(id));
            return NotFound();
        }

        [Route("{id}/shopItems/{itemId}")]
        [HttpPut]
        public IActionResult addShopItem(int id, int itemId)
        {
            Inventory inventory = inventoryService.addShopItem(id, itemId);
            if (inventory != null)
                return Ok(inventory);
            return NotFound();
        }

        [Route("{id}/Ingredients/{ingredientId}")]
        [HttpPut]
        public IActionResult addIngredient(int id, int ingredientId)
        {
            Inventory inventory = inventoryService.addIngredient(id, ingredientId);
            if (inventory != null)
                return Ok(inventory);
            return NotFound();
        }
    }
}
