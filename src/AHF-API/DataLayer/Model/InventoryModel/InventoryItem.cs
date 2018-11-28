using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer.Model.InventoryModel
{
    public class InventoryItem
    {
        public int Id { get; set; }
        public int Quantity { get; set; }
        public Item Item { get; set; }
    }
}
