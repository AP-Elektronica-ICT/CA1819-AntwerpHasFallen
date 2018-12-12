using DataLayer.Model;
using DataLayer.Model.InventoryModel;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace DataLayer
{
    public class GameContext : DbContext
    {
        public GameContext(DbContextOptions<GameContext> options): base(options)
        {

        }

        public DbSet<Game> Games { get; set; }
        public DbSet<Team> Teams { get; set; }
        public DbSet<Player> Players { get; set; }


        public DbSet<Quizpuzzles> Quizpuzzles { get; set; }

        public DbSet<Inventory> Inventories { get; set; }
        public DbSet<InventoryItem> Items { get; set; }
        public DbSet<Ingredient> Ingredients { get; set; }
        public DbSet<ShopItem> ShopItems { get; set; }
        public DbSet<Location> Locations { get; set; }
    }
}
