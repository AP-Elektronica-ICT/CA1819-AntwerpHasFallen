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

        public ShopController(ShopService shopService)
        {
            this.shopService = shopService;
        }

        public IActionResult getAllShopItems()
        {
            List<ShopItem> inventoryItems = new List<ShopItem>();
            shopService.GetShopItems().ForEach(item =>
                inventoryItems.Add(item));

            return Ok(inventoryItems);
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
