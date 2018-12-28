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

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<PreviousLocations>()
                .HasKey(t => new { t.TeamId, t.LocationId });

            modelBuilder.Entity<PreviousLocations>()
                .HasOne(pt => pt.Team)
                .WithMany(p => p.PreviousLocations)
                .HasForeignKey(pt => pt.TeamId);

            modelBuilder.Entity<PreviousLocations>()
                .HasOne(pt => pt.Location)
                .WithMany(t => t.PreviousLocations)
                .HasForeignKey(pt => pt.LocationId);
        }

        public DbSet<Game> Games { get; set; }
        public DbSet<Team> Teams { get; set; }
        public DbSet<Player> Players { get; set; }
        public DbSet<Quizpuzzles> Quizpuzzles { get; set; }
        public DbSet<SubstitionPuzzles> SubstitionPuzzles { get; set; }
        public DbSet<Inventory> Inventories { get; set; }
        public DbSet<InventoryItem> Items { get; set; }
        public DbSet<Ingredient> Ingredients { get; set; }
        public DbSet<ShopItem> ShopItems { get; set; }
        public DbSet<Location> Locations { get; set; }
    }
}
