FROM microsoft/dotnet:2.1-aspnetcore-runtime AS base
WORKDIR /app
EXPOSE 80

FROM microsoft/dotnet:2.1-sdk AS build
WORKDIR /src
COPY ["AntwerpHasFallen/AntwerpHasFallen.csproj", "AntwerpHasFallen/"]
COPY ["DataLayer/DataLayer.csproj", "DataLayer/"]
COPY ["BusinessLayer/BusinessLayer.csproj", "BusinessLayer/"]
RUN dotnet restore "AntwerpHasFallen/AntwerpHasFallen.csproj"
COPY . .
WORKDIR "/src/AntwerpHasFallen"
RUN dotnet build "AntwerpHasFallen.csproj" -c Release -o /app

FROM build AS publish
RUN dotnet publish "AntwerpHasFallen.csproj" -c Release -o /app

FROM base AS final
WORKDIR /app
COPY --from=publish /app .
ENTRYPOINT ["dotnet", "AntwerpHasFallen.dll"]