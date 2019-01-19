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
        private readonly DadService dadService;
        private readonly AnagramService anagramService;

        public LocationController(LocationService locationService, QuizService quizService, SubstitionService substitutionService, DadService dadService, AnagramService anagramService)
        {
            this.locationService = locationService;
            this.quizService = quizService;
            this.substitionService = substitutionService;
            this.dadService = dadService;
            this.anagramService = anagramService;
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

        [Route("quiz/{name}")]
        [HttpGet()]
        public IActionResult getLocationByName(string name)
        {
            return Ok(locationService.GetQuizByName(name));
        }
        [Route("subs/{name}")]
        [HttpGet()]
        public IActionResult getSubsByName(string name)
        {
            return Ok(locationService.GetSubByName(name));
        }

        [Route("dad/{name}")]
        [HttpGet]
        public IActionResult getDadByName(string name)
        {
            return Ok(locationService.GetDadByName(name));
        }

        [Route("anagram/{name}")]
        [HttpGet]
        public IActionResult getAnagramByName(string name)
        {
            return Ok(locationService.GetAnagramByName(name));
        }


    }
}
