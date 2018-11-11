using System;
using Xunit;
using Moq;
using BusinessLayer;

namespace BusinessLayerTest
{
    public class GameServiceTest
    {
        [Theory]
        [InlineData("test test", "testtest")]
        [InlineData("i is tt   bla", "iisttbla")]
        [InlineData("nospace", "nospace")]
        [InlineData("       ", "")]
        public void RemoveWhiteSpacesTest(string s, string value)
        {
            var serviceMock = new Mock<GameService>();
            GameService service = serviceMock.Object;
            string result = service.RemoveWhiteSpaces(s);
            Assert.Equal(result, value);
        }
    }
}
