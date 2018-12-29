using BusinessLayer;
using DataLayer.Model;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace AntwerpHasFallen.Controllers
{
    [Produces("application/json")]
    [Route("api/locations")]
    public class LocationController : Controller
    {
        private readonly LocationService locationService;
        private readonly QuizService quizService;
        private readonly SubstitionService substitionService;

        public LocationController(LocationService locationService, QuizService quizService, SubstitionService substitutionService)
        {
            this.locationService = locationService;
            this.quizService = quizService;
            this.substitionService = substitutionService;
        }

        [HttpGet]
        public IActionResult getAllLocations()
        {
            return Ok(locationService.getLocations());
        }

        [Route("{id}")]
        [HttpGet()]
        public IActionResult getLocation(int id)
        {
            Location location = locationService.GetLocation(id);
            if (location != null)
                return Ok(location);
            else
                return NotFound();
        }


    }
}
