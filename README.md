# ABIMABALL

Jogo local de futebol de cabeças para dois jogadores, feito em Java 21 e JavaFX.

## Jogar no Windows

Abra a página **Releases** do repositório, baixe `ABIMABALL-Windows.zip`, extraia a pasta inteira e execute `ABIMABALL.exe`. O pacote já inclui o Java necessário.

Uma partida dura 90 segundos ou termina quando alguém marca 5 gols.

| Ação | Player 1 | Player 2 |
| --- | --- | --- |
| Mover | `A` / `D` | `←` / `→` |
| Pular | `W` | `↑` |
| Chutar | `C` | `Espaço` |
| Poder | `S` | `↓` |
| Pausar | `Esc` | `Esc` |

Cada poder dura 5 segundos e tem 20 segundos de recarga.

## Rodar o código

Requisitos: JDK 21. No Windows, configure `JAVA_HOME` para a pasta do JDK e execute:

```powershell
.\mvnw.cmd clean javafx:run
```

No Linux/macOS:

```bash
./mvnw clean javafx:run
```

Para compilar e executar os testes:

```powershell
.\mvnw.cmd clean package
```

O JAR é criado em `target/ABIMABALL.jar`. As dependências JavaFX do artefato são específicas do sistema usado no build; para os jogadores, prefira o pacote da página Releases.

## Publicar uma versão no GitHub

O workflow em `.github/workflows/release.yml` testa o projeto e monta automaticamente um aplicativo portátil para Windows. Em pushes comuns ele fica disponível como artefato da execução. Ao enviar uma tag, também cria uma GitHub Release:

```bash
git tag v1.0.0
git push origin v1.0.0
```

## Próximos passos possíveis

- Sons, música e animações de chute
- Modo de um jogador contra IA
- Seleção de arena e opções de volume
- Balanceamento dos poderes após testes com jogadores
