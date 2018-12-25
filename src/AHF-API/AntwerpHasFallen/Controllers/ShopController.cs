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
    [Route("api/shop")]
    public class ShopController : Controller
    {
        private readonly ShopService shopService;
        private readonly InventoryService inventoryService;

        public ShopController(ShopService shopService, InventoryService inventoryService)
        {
            this.shopService = shopService;
            this.inventoryService = inventoryService;
        }

        public IActionResult getAllShopItems()
        {
            List<ShopItem> inventoryItems = new List<ShopItem>();
            shopService.GetShopItems().ForEach(item =>
                inventoryItems.Add(item));

            return Ok(inventoryItems);
        }

        [Route("buy/{shopItemId}")]
        [HttpPut]
        public IActionResult buyShopItem(int shopItemId, [FromBody] int teamId)
        {
            Inventory inventory = shopService.buyShopItem(shopItemId, teamId);
            if (inventory == null)
                return NotFound("item or team does not exist, or team money is insufficient");
            else
                return Ok(inventory);
        }

        [Route("items")]
        [HttpGet]
        public IActionResult getAllItems()
        {
            if (shopService.GetItems() != null)
                return Ok(shopService.GetItems());
            return NotFound();
        }

        [Route("ingredients")]
        [HttpGet]
        public IActionResult getAllIngredients()
        {
            if (shopService.GetIngredients() != null)
                return Ok(shopService.GetIngredients());
            return NotFound();
        }
    }
}
