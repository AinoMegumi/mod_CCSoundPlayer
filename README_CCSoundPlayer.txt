
	CC Sound Player 0.2.2

		��ҁFIunius


�E����

	ComputerCraft�̎��Ӌ@���Turtle�A�b�v�O���[�h��ǉ�����A�h�I��
	ComputerCraft�̃R���s���[�^���ɂ��鉹���t�@�C���i*.ogg/*.wav�j���Đ��ł���


�E�O����i�K�{�j

	Minecraft 1.2.5�@�i�V���O���v���C�̂ݑΉ��j
	Minecraft Forge 171
	ComeputerCraft 1.41


�E�C���X�g�[�����@

	Mod��zip�t�@�C����mods�t�H���_�ɒu��


�E�g�p���@

	�E���Ӌ@��
	����mod�Œǉ������gSound Player Block�h��ComputerCraft�̃R���s���[�^�ɐڑ����A�v���O�����ő��삷��
	Sound Player Block�̃u���b�NID�i�f�t�H���g��1759�j�� config/mod_CCSoundPlayer.cfg �ŕύX�\
	peripheral.getType���\�b�h�ł̖߂�l�� soundPlayer

	�ETurtle�A�b�v�O���[�h
	Turtle�ƁgSound Player Block�h���N���t�g����Turtle�ɑ�������
	Sound-Player Turtle�̃A�b�v�O���[�hID�i�f�t�H���g��75�j�� config/mod_CCSoundPlayer.cfg �ŕύX�\
	Turtle�̌`�e���� Sound-Player�Aperipheral.getType���\�b�h�ł̖߂�l�� soundPlayer


�E���V�s

	�ESound Player Block

		SRS	S���΃u���b�N
		SNS	R�����b�h�X�g�[���p�E�_�[
		SSS	N�������u���b�N

	�ESound-Player Turtle

		T#	T��Turtle
			#��Sound Player Block


�E���Ӌ@��̃��\�b�h

	sp = peripheral.wrap( <dir> )

	sp.play( filename, isStreaming, volume_or_pitch )

		���̎��Ӌ@��ŉ����t�@�C�����Đ�����

		filename�i������j�F�R���s���[�^����wav�܂���ogg�t�@�C���̖��O
		isStreaming�i�^�U�l�j�F�ȗ��Btrue�̂Ƃ��̓X�g���[�~���O���[�h�Afalse���ȗ����͌��ʉ����[�h
		volume_or_pitch�i���l�j�F�ȗ��B���ʂ܂��͉����B�ǂ���Ȃ̂���isStreaming�̒l�Ō��܂�B���L�Q��

		�@	�X�g���[�~���O���[�h�F
				�E��r�I�傫�ȃt�@�C���ł������ɍĐ����J�n�ł���
				�Evolume_or_pitch �Łu���ʁv���w��ł���i0.0�`1.0�̐��l�j
				�E���ۂ̉��ʂ́A���Ӌ@��Ŏw�肵�����ʁ~Minecraft�I�v�V������Music����
				�Estop���\�b�h�ōĐ����~�ł���
				�E��̎��Ӌ@��u���b�N�œ����ɕ����̋Ȃ��Đ��ł��Ȃ�
�@�@�@�@�@�@�@�@�@�@�@�@���ʉ����[�h�F
				�Evolume_or_pitch �Łu�����v���w��ł���i0.0�`24.0�̐��l�j
				�E���ʂ�Minecraft�I�v�V������Sound���ʂŕύX�\
				�E��̎��Ӌ@��u���b�N�ŕ����̉����𓯎��Đ��ł���
				�E��x�Đ����n�߂�Ǝ��Ӌ@�킩��͎~�߂��Ȃ�
				�E�傫�ȃt�@�C�����Đ����悤�Ƃ���ƒ����ԃt���[�Y����

	sp.stop()

		���̎��Ӌ@�킪�X�g���[�~���O�Đ����Ȃ�΂�����~����
		���̎��Ӌ@��ŃX�g���[�~���O�Đ����������t�@�C����Minecraft�̃T�E���h�V�X�e������������
		�T�E���h�V�X�e�����������ꂽ�����t�@�C���͍ĂуA�N�Z�X���\�ɂȂ�

	sp.volume( vol )

		���̎��Ӌ@��̃X�g���[�~���O�Đ����̉��ʂ�0.0�`1.0�̐��l�Ŏw�肷��
		���ۂ̉��ʂ́A���Ӌ@��Ŏw�肵�����ʁ~Minecraft�I�v�V������Music����

	sp.isPlaying()

		���̎��Ӌ@�킪�X�g���[�~���O�Đ����Ȃ��true��Ԃ�
		����ȊO�ł�false��Ԃ�


�E�Ɛӎ���

	��mod�̗��p�ɂ��Ă͂��ׂė��p�҂̐ӔC�ōs���A��mod�𗘗p�������Ƃɂ�蔭�����������Ȃ鑹�Q�ɑ΂��Ă���mod�̍�҂͐ӔC�𕉂�Ȃ����ƂƂ��܂�
	���[���h��v���O�����A�����t�@�C���̃o�b�N�A�b�v�͖Y�ꂸ��

