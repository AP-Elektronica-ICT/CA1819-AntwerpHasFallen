using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using DataLayer;
using BusinessLayer;

namespace AntwerpHasFallen
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            /*services.AddDbContext<GameContext>(options => options.UseSqlServer(
                Configuration.GetConnectionString("DefaultConnection")
                )
            );*/

<<<<<<< HEAD

            /*services.AddDbContext<GameContext>(options => options.UseMySql(

=======
            services.AddDbContext<GameContext>(options => options.UseMySql(
>>>>>>> master
                Configuration.GetConnectionString("DefaultConnection")
                )
            );

            services.AddScoped<GameService>();
            services.AddScoped<TeamService>();


            services.AddScoped<QuizService>();
            services.AddScoped<PlayerService>();
            services.AddScoped<InventoryService>();
            services.AddScoped<ShopService>();
            services.AddScoped<LocationService>();
            services.AddScoped<SubstitionService>();

            services.AddMvc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, GameContext context)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            DbInitializer.Initialize(context);

            app.UseMvc();
        }
    }
}
