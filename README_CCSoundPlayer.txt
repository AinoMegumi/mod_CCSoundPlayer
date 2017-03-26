
	CC Sound Player 0.2.2

		作者：Iunius


・説明

	ComputerCraftの周辺機器とTurtleアップグレードを追加するアドオン
	ComputerCraftのコンピュータ内にある音声ファイル（*.ogg/*.wav）を再生できる


・前提環境（必須）

	Minecraft 1.2.5　（シングルプレイのみ対応）
	Minecraft Forge 171
	ComeputerCraft 1.41


・インストール方法

	Modのzipファイルをmodsフォルダに置く


・使用方法

	・周辺機器
	このmodで追加される“Sound Player Block”をComputerCraftのコンピュータに接続し、プログラムで操作する
	Sound Player BlockのブロックID（デフォルト＝1759）は config/mod_CCSoundPlayer.cfg で変更可能
	peripheral.getTypeメソッドでの戻り値は soundPlayer

	・Turtleアップグレード
	Turtleと“Sound Player Block”をクラフトしてTurtleに装着する
	Sound-Player TurtleのアップグレードID（デフォルト＝75）は config/mod_CCSoundPlayer.cfg で変更可能
	Turtleの形容詞は Sound-Player、peripheral.getTypeメソッドでの戻り値は soundPlayer


・レシピ

	・Sound Player Block

		SRS	S＝石ブロック
		SNS	R＝レッドストーンパウダー
		SSS	N＝音符ブロック

	・Sound-Player Turtle

		T#	T＝Turtle
			#＝Sound Player Block


・周辺機器のメソッド

	sp = peripheral.wrap( <dir> )

	sp.play( filename, isStreaming, volume_or_pitch )

		その周辺機器で音声ファイルを再生する

		filename（文字列）：コンピュータ内のwavまたはoggファイルの名前
		isStreaming（真偽値）：省略可。trueのときはストリーミングモード、falseか省略時は効果音モード
		volume_or_pitch（数値）：省略可。音量または音程。どちらなのかはisStreamingの値で決まる。下記参照

		　	ストリーミングモード：
				・比較的大きなファイルでもすぐに再生を開始できる
				・volume_or_pitch で「音量」を指定できる（0.0～1.0の数値）
				・実際の音量は、周辺機器で指定した音量×MinecraftオプションのMusic音量
				・stopメソッドで再生を停止できる
				・一つの周辺機器ブロックで同時に複数の曲を再生できない
　　　　　　　　　　　　効果音モード：
				・volume_or_pitch で「音程」を指定できる（0.0～24.0の数値）
				・音量はMinecraftオプションのSound音量で変更可能
				・一つの周辺機器ブロックで複数の音声を同時再生できる
				・一度再生を始めると周辺機器からは止められない
				・大きなファイルを再生しようとすると長時間フリーズする

	sp.stop()

		その周辺機器がストリーミング再生中ならばそれを停止する
		その周辺機器でストリーミング再生した音声ファイルをMinecraftのサウンドシステムから解放する
		サウンドシステムから解放された音声ファイルは再びアクセスが可能になる

	sp.volume( vol )

		その周辺機器のストリーミング再生時の音量を0.0～1.0の数値で指定する
		実際の音量は、周辺機器で指定した音量×MinecraftオプションのMusic音量

	sp.isPlaying()

		その周辺機器がストリーミング再生中ならばtrueを返す
		それ以外ではfalseを返す


・免責事項

	当modの利用についてはすべて利用者の責任で行い、当modを利用したことにより発生したいかなる損害に対しても当modの作者は責任を負わないこととします
	ワールドやプログラム、音声ファイルのバックアップは忘れずに

