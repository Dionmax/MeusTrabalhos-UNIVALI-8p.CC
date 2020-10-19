########################################################################
## Description: Mineração de dados
##
## Maintainer: UNIVALI / EMCT / CC
## Author: Dionmax FN / João F.
## Created: Dom Set 07 2020
## Version: 0.0.1
##
## URL:
## Doc URL:
##
## Database info: https://www.kaggle.com/leonardopena/top50spotify2019
##
### Commentary:
##
### Code:
########################################################################

########################################################################
######@> Direcionando o R para a pasta de trabalho...

######@> Escolhendo a pasta de trabalho...
# setwd("../")

########################################################################
######@> Instalando e carregando os pacotes das analises...

# ######@> Instalando os pacotes readxl, dplyr, ggplot2 e survey...
# install.packages(c("readxl", "dplyr", "ggplot2", "survey"),
#                  dependencies = TRUE)

######@> Carregando os pacotes recem instalados...
library(readxl)
library(dplyr)
library(ggplot2)
# library(survey)

#########################################################################
######@> Carregando os dados para as analises...

######@> Dados
MainDF <- read_excel(path = "Spotify50.xlsx",
                   sheet = "Spotify50")

#########################################################################
######@> Criando DF especificos para comparações dos dados

######@> 1 - Gênero x Volume
## Se o volume difere muito de acordo com o gênero

######@> Separando colunas de gênero e volume e renomendo-as
genXvol <- MainDF %>%
  select("Gênero" = Genre, "Volume (db)" = Loudness..dB..) %>%
  distinct()

######@> Criando um gráfico de barras gênero
genXvolPlot <- ggplot(data = genXvol, aes(y = `Gênero`, x = `Volume (db)`)) +
  geom_bar(stat = "identity")

######@> Gráfico Gênero X Volume
genXvolPlot

######@> Tornando os valores de volume positivo para melhor entendimento
genXvolPos <- data.frame("Gênero" = genXvol$Gênero, 
                         "Volume (db)" = genXvol$`Volume (db)` * -1)

######@> Criando um gráfico de barras gênero com valores positivos
genXvolPlotPos <- ggplot(data = genXvolPos, aes(y = `Gênero`, x = `Volume..db.`)) +
  geom_bar(stat = "identity")

######@> Gráfico Gênero X Volume com valores positivos
genXvolPlotPos

######@>-----------------------------------------------------------------

######@ 2 - Energia da música x Ao vivo ou estúdio
## Se músicas ao vivo ou de estúdio são mais ou menos energéticas

######@> Separando colunas de Energia e liveness e renomendo-as
enerXLive <- MainDF %>%
  select("Energia" = Energy, "Liveness" = Liveness)

######@> Gráfico com a média em linha da comparação entre
## energia e tipo de gravação
enerXLivePlot <- ggplot(data = enerXLive, aes(x = `Liveness`, y = `Energia`)) +
  geom_point() +
  stat_summary(aes(y = `Energia`, group = 1), 
               fun = mean, colour="red", geom="line",group = 1)

######@> Gráfico Energia e tipo de gravação
enerXLivePlot

######@> Agrupando e fazendo a média dos valores
enerXLiveMean <- enerXLive %>%
  group_by(Liveness) %>%
  summarise("Energia" = mean(Energia), .groups = 'drop')

######@> Gráfico com a média em linha da comparação entre
## energia e tipo de gravação filtrado
enerXLivePlotMean <- ggplot(data = enerXLiveMean, 
                            aes(x = `Liveness`, y = `Energia`)) +
  geom_point() +
  stat_summary(aes(y = `Energia`, group = 1), 
               fun = mean, colour="red", geom="line",group = 1)

######@> Gráfico Energia e tipo de gravação filtrado
enerXLivePlotMean

######@>-----------------------------------------------------------------

######@ 3 - Ao vivo ou estúdio x Clima da música
## Se músicas ao mais energéticas possuem um clima mais feliz ou não

######@> Separando colunas de liveness e clima e renomendo-as
liveXMood <- MainDF %>%
  select("Clima" = Valence., "Liveness" = Liveness)

######@> Criando gráfico da relação entre clima e liveness
liveXMoodPlot <- ggplot(data = liveXMood, 
                        aes(x = `Liveness`, y = `Clima`)) +
  geom_point() +
  stat_summary(aes(y = `Clima`, group = 1), 
               fun = mean, colour="blue", geom="line",group = 1)

######@> Gráfico liveness e clima da música filtrado
liveXMoodPlot

######@>-----------------------------------------------------------------

######@ 4 - Duração média da música tem algo haver com gênero

######@> Separando colunas de duração e gênero e fazendo a média
duracaoXGen <- MainDF %>%
  select("Duração" = Length., "Gênero" = Genre) %>% 
  group_by(`Gênero`) %>%
  summarise("Duração (seg)" = mean(`Duração`), .groups = 'drop')

######@> Criando gráfico da relação entre Duração e gênero
duracaoXGenPlot <- ggplot(data = duracaoXGen, aes(x = `Duração (seg)`, y = `Gênero`)) +
  geom_bar(stat = "identity")

######@> Gráfico duração e gênero média
duracaoXGenPlot

######@>-----------------------------------------------------------------

######@ 5 - Clima da música e energia
## Se músicas ao mais energéticas possuem um clima mais feliz ou não

######@> Separando colunas de energia e clima e renomendo-as
liveXEnerg <- MainDF %>%
  select("Clima" = Valence.,"Energia" = Energy)

######@> Criando gráfico da relação entre clima e liveness
liveXEnergPlot <- ggplot(data = liveXEnerg, 
                        aes(x = `Energia`, y = `Clima`)) +
  geom_point() +
  stat_summary(aes(y = `Clima`, group = 1), 
               fun = mean, colour="blue", geom="line",group = 1)

######@> Gráfico Energia e tipo de gravação filtrado
liveXEnergPlot

######@>-----------------------------------------------------------------

######@ 6 - Artista mais escutado e a duração média das músicas

######@> Separando colunas de duração média e artista mais escutado
ArtisXduracao <- MainDF %>%
  select("Duração" = Length., "Artista" = Artist.Name) %>% 
  group_by(`Artista`) %>%
  summarise("Duração (seg)" = mean(`Duração`), .groups = 'drop')

######@> Criando gráfico da relação entre Duração e gênero
ArtisXduracaoPlot <- ggplot(data = ArtisXduracao, aes(x = `Duração (seg)`, y = `Artista`)) +
  geom_bar(stat = "identity")

######@> Gráfico duração média e Artisca
ArtisXduracaoPlot

#########################################################################
######@> Gráficos comparativos finais

######@> Gráfico Gênero X Volume
genXvolPlot

######@> Gráfico Gênero X Volume com valores positivos
genXvolPlotPos

######@> Gráfico Energia e tipo de gravação
enerXLivePlot

######@> Gráfico Energia e tipo de gravação filtrado
enerXLivePlotMean

######@> Ao vivo e clima
liveXMoodPlot

######@> Gráfico duração e gênero média
duracaoXGenPlot

######@> Gráfico clima e energia
liveXEnergPlot

######@> Artista e duração
ArtisXduracaoPlot
















