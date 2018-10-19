using DataLayer.Model;
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
    }
}
