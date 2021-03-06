########################################################################
## Description: Minera��o de dados
##
## Maintainer: UNIVALI / EMCT / CC
## Author: Dionmax FN / Jo�o F.
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
######@> Criando DF especificos para compara��es dos dados

######@> 1 - G�nero x Volume
## Se o volume difere muito de acordo com o g�nero

######@> Separando colunas de g�nero e volume e renomendo-as
genXvol <- MainDF %>%
  select("G�nero" = Genre, "Volume (db)" = Loudness..dB..) %>%
  distinct()

######@> Criando um gr�fico de barras g�nero
genXvolPlot <- ggplot(data = genXvol, aes(y = `G�nero`, x = `Volume (db)`)) +
  geom_bar(stat = "identity")

######@> Gr�fico G�nero X Volume
genXvolPlot

######@> Tornando os valores de volume positivo para melhor entendimento
genXvolPos <- data.frame("G�nero" = genXvol$G�nero, 
                         "Volume (db)" = genXvol$`Volume (db)` * -1)

######@> Criando um gr�fico de barras g�nero com valores positivos
genXvolPlotPos <- ggplot(data = genXvolPos, aes(y = `G�nero`, x = `Volume..db.`)) +
  geom_bar(stat = "identity")

######@> Gr�fico G�nero X Volume com valores positivos
genXvolPlotPos

######@>-----------------------------------------------------------------

######@ 2 - Energia da m�sica x Ao vivo ou est�dio
## Se m�sicas ao vivo ou de est�dio s�o mais ou menos energ�ticas

######@> Separando colunas de Energia e liveness e renomendo-as
enerXLive <- MainDF %>%
  select("Energia" = Energy, "Liveness" = Liveness)

######@> Gr�fico com a m�dia em linha da compara��o entre
## energia e tipo de grava��o
enerXLivePlot <- ggplot(data = enerXLive, aes(x = `Liveness`, y = `Energia`)) +
  geom_point() +
  stat_summary(aes(y = `Energia`, group = 1), 
               fun = mean, colour="red", geom="line",group = 1)

######@> Gr�fico Energia e tipo de grava��o
enerXLivePlot

######@> Agrupando e fazendo a m�dia dos valores
enerXLiveMean <- enerXLive %>%
  group_by(Liveness) %>%
  summarise("Energia" = mean(Energia), .groups = 'drop')

######@> Gr�fico com a m�dia em linha da compara��o entre
## energia e tipo de grava��o filtrado
enerXLivePlotMean <- ggplot(data = enerXLiveMean, 
                            aes(x = `Liveness`, y = `Energia`)) +
  geom_point() +
  stat_summary(aes(y = `Energia`, group = 1), 
               fun = mean, colour="red", geom="line",group = 1)

######@> Gr�fico Energia e tipo de grava��o filtrado
enerXLivePlotMean

######@>-----------------------------------------------------------------

######@ 3 - Ao vivo ou est�dio x Clima da m�sica
## Se m�sicas ao mais energ�ticas possuem um clima mais feliz ou n�o

######@> Separando colunas de liveness e clima e renomendo-as
liveXMood <- MainDF %>%
  select("Clima" = Valence., "Liveness" = Liveness)

######@> Criando gr�fico da rela��o entre clima e liveness
liveXMoodPlot <- ggplot(data = liveXMood, 
                        aes(x = `Liveness`, y = `Clima`)) +
  geom_point() +
  stat_summary(aes(y = `Clima`, group = 1), 
               fun = mean, colour="blue", geom="line",group = 1)

######@> Gr�fico liveness e clima da m�sica filtrado
liveXMoodPlot

######@>-----------------------------------------------------------------

######@ 4 - Dura��o m�dia da m�sica tem algo haver com g�nero

######@> Separando colunas de dura��o e g�nero e fazendo a m�dia
duracaoXGen <- MainDF %>%
  select("Dura��o" = Length., "G�nero" = Genre) %>% 
  group_by(`G�nero`) %>%
  summarise("Dura��o (seg)" = mean(`Dura��o`), .groups = 'drop')

######@> Criando gr�fico da rela��o entre Dura��o e g�nero
duracaoXGenPlot <- ggplot(data = duracaoXGen, aes(x = `Dura��o (seg)`, y = `G�nero`)) +
  geom_bar(stat = "identity")

######@> Gr�fico dura��o e g�nero m�dia
duracaoXGenPlot

######@>-----------------------------------------------------------------

######@ 5 - Clima da m�sica e energia
## Se m�sicas ao mais energ�ticas possuem um clima mais feliz ou n�o

######@> Separando colunas de energia e clima e renomendo-as
liveXEnerg <- MainDF %>%
  select("Clima" = Valence.,"Energia" = Energy)

######@> Criando gr�fico da rela��o entre clima e liveness
liveXEnergPlot <- ggplot(data = liveXEnerg, 
                        aes(x = `Energia`, y = `Clima`)) +
  geom_point() +
  stat_summary(aes(y = `Clima`, group = 1), 
               fun = mean, colour="blue", geom="line",group = 1)

######@> Gr�fico Energia e tipo de grava��o filtrado
liveXEnergPlot

######@>-----------------------------------------------------------------

######@ 6 - Artista mais escutado e a dura��o m�dia das m�sicas

######@> Separando colunas de dura��o m�dia e artista mais escutado
ArtisXduracao <- MainDF %>%
  select("Dura��o" = Length., "Artista" = Artist.Name) %>% 
  group_by(`Artista`) %>%
  summarise("Dura��o (seg)" = mean(`Dura��o`), .groups = 'drop')

######@> Criando gr�fico da rela��o entre Dura��o e g�nero
ArtisXduracaoPlot <- ggplot(data = ArtisXduracao, aes(x = `Dura��o (seg)`, y = `Artista`)) +
  geom_bar(stat = "identity")

######@> Gr�fico dura��o m�dia e Artisca
ArtisXduracaoPlot

#########################################################################
######@> Gr�ficos comparativos finais

######@> Gr�fico G�nero X Volume
genXvolPlot

######@> Gr�fico G�nero X Volume com valores positivos
genXvolPlotPos

######@> Gr�fico Energia e tipo de grava��o
enerXLivePlot

######@> Gr�fico Energia e tipo de grava��o filtrado
enerXLivePlotMean

######@> Ao vivo e clima
liveXMoodPlot

######@> Gr�fico dura��o e g�nero m�dia
duracaoXGenPlot

######@> Gr�fico clima e energia
liveXEnergPlot

######@> Artista e dura��o
ArtisXduracaoPlot
















