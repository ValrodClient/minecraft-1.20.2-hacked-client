package com.valrod.client.ui.gui.components;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PasswordBox extends AbstractWidget implements Renderable {
	private static final WidgetSprites SPRITES = new WidgetSprites(new ResourceLocation("widget/text_field"), new ResourceLocation("widget/text_field_highlighted"));
	public static final int BACKWARDS = -1;
	public static final int FORWARDS = 1;
	private static final int CURSOR_INSERT_WIDTH = 1;
	private static final int CURSOR_INSERT_COLOR = -3092272;
	private static final String CURSOR_APPEND_CHARACTER = "_";
	public static final int DEFAULT_TEXT_COLOR = 14737632;
	private static final int CURSOR_BLINK_INTERVAL_MS = 300;
	private final Font font;
	private String value = "";
	private int maxLength = 64;
	private boolean bordered = true;
	private boolean canLoseFocus = true;
	private boolean isEditable = true;
	private int displayPos;
	private int cursorPos;
	private int highlightPos;
	private int textColor = 14737632;
	private int textColorUneditable = 7368816;
	@Nullable
	private String suggestion;
	@Nullable
	private Consumer<String> responder;
	private Predicate<String> filter = Objects::nonNull;
	private BiFunction<String, Integer, FormattedCharSequence> formatter = (p_94147_, p_94148_) -> {
		return FormattedCharSequence.forward(p_94147_, Style.EMPTY);
	};
	@Nullable
	private Component hint;
	private long focusedTime = Util.getMillis();

	public PasswordBox(Font font, int width, int height, Component component) {
		this(font, 0, 0, width, height, component);
	}

	public PasswordBox(Font font, int x, int y, int width, int height, Component component) {
		this(font, x, y, width, height, (PasswordBox)null, component);
	}

	public PasswordBox(Font font, int x, int y, int width, int height, @Nullable PasswordBox template, Component component) {
		super(x, y, width, height, component);
		this.font = font;
		if (template != null) {
			this.setValue(template.getValue());
		}

	}

	public void setResponder(Consumer<String> p_94152_) {
		this.responder = p_94152_;
	}

	public void setFormatter(BiFunction<String, Integer, FormattedCharSequence> p_94150_) {
		this.formatter = p_94150_;
	}

	protected MutableComponent createNarrationMessage() {
		Component component = this.getMessage();
		return Component.translatable("gui.narrate.editBox", component, this.value);
	}

	public void setValue(String value) {
		if (this.filter.test(value)) {
			if (value.length() > this.maxLength) {
				this.value = value.substring(0, this.maxLength);
			} else {
				this.value = value;
			}

			this.moveCursorToEnd(false);
			this.setHighlightPos(this.cursorPos);
			this.onValueChange(value);
		}
	}

	public String getValue() {
		return this.value;
	}

	public String getHighlighted() {
		int i = Math.min(this.cursorPos, this.highlightPos);
		int j = Math.max(this.cursorPos, this.highlightPos);
		return this.value.substring(i, j);
	}

	public void setFilter(Predicate<String> p_94154_) {
		this.filter = p_94154_;
	}

	public void insertText(String p_94165_) {
		int i = Math.min(this.cursorPos, this.highlightPos);
		int j = Math.max(this.cursorPos, this.highlightPos);
		int k = this.maxLength - this.value.length() - (i - j);
		String s = SharedConstants.filterText(p_94165_);
		int l = s.length();
		if (k < l) {
			s = s.substring(0, k);
			l = k;
		}

		String s1 = (new StringBuilder(this.value)).replace(i, j, s).toString();
		if (this.filter.test(s1)) {
			this.value = s1;
			this.setCursorPosition(i + l);
			this.setHighlightPos(this.cursorPos);
			this.onValueChange(this.value);
		}
	}

	private void onValueChange(String p_94175_) {
		if (this.responder != null) {
			this.responder.accept(p_94175_);
		}

	}

	private void deleteText(int p_94218_) {
		if (Screen.hasControlDown()) {
			this.deleteWords(p_94218_);
		} else {
			this.deleteChars(p_94218_);
		}

	}

	public void deleteWords(int p_94177_) {
		if (!this.value.isEmpty()) {
			if (this.highlightPos != this.cursorPos) {
				this.insertText("");
			} else {
				this.deleteChars(this.getWordPosition(p_94177_) - this.cursorPos);
			}
		}
	}

	public void deleteChars(int p_94181_) {
		if (!this.value.isEmpty()) {
			if (this.highlightPos != this.cursorPos) {
				this.insertText("");
			} else {
				int i = this.getCursorPos(p_94181_);
				int j = Math.min(i, this.cursorPos);
				int k = Math.max(i, this.cursorPos);
				if (j != k) {
					String s = (new StringBuilder(this.value)).delete(j, k).toString();
					if (this.filter.test(s)) {
						this.value = s;
						this.moveCursorTo(j, false);
					}
				}
			}
		}
	}

	public int getWordPosition(int p_94185_) {
		return this.getWordPosition(p_94185_, this.getCursorPosition());
	}

	private int getWordPosition(int p_94129_, int p_94130_) {
		return this.getWordPosition(p_94129_, p_94130_, true);
	}

	private int getWordPosition(int p_94141_, int p_94142_, boolean p_94143_) {
		int i = p_94142_;
		boolean flag = p_94141_ < 0;
		int j = Math.abs(p_94141_);

		for(int k = 0; k < j; ++k) {
			if (!flag) {
				int l = this.value.length();
				i = this.value.indexOf(32, i);
				if (i == -1) {
					i = l;
				} else {
					while(p_94143_ && i < l && this.value.charAt(i) == ' ') {
						++i;
					}
				}
			} else {
				while(p_94143_ && i > 0 && this.value.charAt(i - 1) == ' ') {
					--i;
				}

				while(i > 0 && this.value.charAt(i - 1) != ' ') {
					--i;
				}
			}
		}

		return i;
	}

	public void moveCursor(int p_94189_, boolean p_297286_) {
		this.moveCursorTo(this.getCursorPos(p_94189_), p_297286_);
	}

	private int getCursorPos(int p_94221_) {
		return Util.offsetByCodepoints(this.value, this.cursorPos, p_94221_);
	}

	public void moveCursorTo(int p_94193_, boolean p_300521_) {
		this.setCursorPosition(p_94193_);
		if (!p_300521_) {
			this.setHighlightPos(this.cursorPos);
		}

		this.onValueChange(this.value);
	}

	public void setCursorPosition(int p_94197_) {
		this.cursorPos = Mth.clamp(p_94197_, 0, this.value.length());
		this.scrollTo(this.cursorPos);
	}

	public void moveCursorToStart(boolean p_299543_) {
		this.moveCursorTo(0, p_299543_);
	}

	public void moveCursorToEnd(boolean p_297711_) {
		this.moveCursorTo(this.value.length(), p_297711_);
	}

	public boolean keyPressed(int p_94132_, int p_94133_, int p_94134_) {
		if (!this.canConsumeInput()) {
			return false;
		} else if (Screen.isSelectAll(p_94132_)) {
			this.moveCursorToEnd(false);
			this.setHighlightPos(0);
			return true;
		} else if (Screen.isCopy(p_94132_)) {
			Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
			return true;
		} else if (Screen.isPaste(p_94132_)) {
			if (this.isEditable) {
				this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
			}

			return true;
		} else if (Screen.isCut(p_94132_)) {
			Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
			if (this.isEditable) {
				this.insertText("");
			}

			return true;
		} else {
			switch (p_94132_) {
				case 259:
					if (this.isEditable) {
						this.deleteText(-1);
					}

					return true;
				case 260:
				case 264:
				case 265:
				case 266:
				case 267:
				default:
					return false;
				case 261:
					if (this.isEditable) {
						this.deleteText(1);
					}

					return true;
				case 262:
					if (Screen.hasControlDown()) {
						this.moveCursorTo(this.getWordPosition(1), Screen.hasShiftDown());
					} else {
						this.moveCursor(1, Screen.hasShiftDown());
					}

					return true;
				case 263:
					if (Screen.hasControlDown()) {
						this.moveCursorTo(this.getWordPosition(-1), Screen.hasShiftDown());
					} else {
						this.moveCursor(-1, Screen.hasShiftDown());
					}

					return true;
				case 268:
					this.moveCursorToStart(Screen.hasShiftDown());
					return true;
				case 269:
					this.moveCursorToEnd(Screen.hasShiftDown());
					return true;
			}
		}
	}

	public boolean canConsumeInput() {
		return this.isVisible() && this.isFocused() && this.isEditable();
	}

	public boolean charTyped(char p_94122_, int p_94123_) {
		if (!this.canConsumeInput()) {
			return false;
		} else if (SharedConstants.isAllowedChatCharacter(p_94122_)) {
			if (this.isEditable) {
				this.insertText(Character.toString(p_94122_));
			}

			return true;
		} else {
			return false;
		}
	}

	public void onClick(double p_279417_, double p_279437_) {
		int i = Mth.floor(p_279417_) - this.getX();
		if (this.bordered) {
			i -= 4;
		}

		String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
		this.moveCursorTo(this.font.plainSubstrByWidth(s, i).length() + this.displayPos, Screen.hasShiftDown());
	}

	public void playDownSound(SoundManager p_279245_) {
	}

	public void renderWidget(GuiGraphics g, int p_281594_, int p_282100_, float p_283101_) {
		if (this.isVisible()) {
			if (this.isBordered()) {
				ResourceLocation textureLocation = SPRITES.get(this.isActive(), this.isFocused());
				g.blitSprite(textureLocation, this.getX(), this.getY(), this.getWidth(), this.getHeight());
			}

			int textColorToUse = this.isEditable ? this.textColor : this.textColorUneditable;
			int cursorOffset = this.cursorPos - this.displayPos;
			String visibleText = this.font.plainSubstrByWidth("*".repeat(this.value.length()).substring(this.displayPos), this.getInnerWidth());
			boolean isCursorVisible = cursorOffset >= 0 && cursorOffset <= visibleText.length();
			boolean shouldBlinkCursor = this.isFocused() && (Util.getMillis() - this.focusedTime) / 300L % 2L == 0L && isCursorVisible;
			int textStartX = this.bordered ? this.getX() + 4 : this.getX();
			int textStartY = this.bordered ? this.getY() + (this.height - 8) / 2 : this.getY();
			int currentTextX = textStartX;
			int highlightPositionOffset = Mth.clamp(this.highlightPos - this.displayPos, 0, visibleText.length());
			if (!visibleText.isEmpty()) {
				String textBeforeCursor = isCursorVisible ? visibleText.substring(0, cursorOffset) : visibleText;
				currentTextX = g.drawString(this.font, this.formatter.apply(textBeforeCursor, this.displayPos), textStartX, textStartY, textColorToUse);
			}

			boolean isCursorPastText = this.cursorPos < this.value.length() || this.value.length() >= this.getMaxLength();
			int cursorXPosition = currentTextX;
			if (!isCursorVisible) {
				cursorXPosition = cursorOffset > 0 ? textStartX + this.width : textStartX;
			} else if (isCursorPastText) {
				cursorXPosition = currentTextX - 1;
				--currentTextX;
			}

			if (!visibleText.isEmpty() && isCursorVisible && cursorOffset < visibleText.length()) {
//				g.drawString(this.font, this.formatter.apply(visibleText.substring(cursorOffset), this.cursorPos), currentTextX, textStartY, textColorToUse);
				g.drawString(this.font, this.formatter.apply(visibleText.substring(cursorOffset), this.cursorPos), currentTextX, textStartY, textColorToUse);
			}

			if (this.hint != null && visibleText.isEmpty() && !this.isFocused()) {
				g.drawString(this.font, this.hint, currentTextX, textStartY, textColorToUse);
			}

			if (!isCursorPastText && this.suggestion != null) {
				g.drawString(this.font, this.suggestion, cursorXPosition - 1, textStartY, -8355712);
			}

			if (shouldBlinkCursor) {
				if (isCursorPastText) {
					g.fill(RenderType.guiOverlay(), cursorXPosition, textStartY - 1, cursorXPosition + 1, textStartY + 1 + 9, -3092272);
				} else {
					g.drawString(this.font, "_", cursorXPosition, textStartY, textColorToUse);
				}
			}

			if (highlightPositionOffset != cursorOffset) {
				int highlightEndX = textStartX + this.font.width(visibleText.substring(0, highlightPositionOffset));
				this.renderHighlight(g, cursorXPosition, textStartY - 1, highlightEndX - 1, textStartY + 1 + 9);
			}

		}
	}

	private void renderHighlight(GuiGraphics p_281400_, int p_265338_, int p_265693_, int p_265618_, int p_265584_) {
		if (p_265338_ < p_265618_) {
			int i = p_265338_;
			p_265338_ = p_265618_;
			p_265618_ = i;
		}

		if (p_265693_ < p_265584_) {
			int j = p_265693_;
			p_265693_ = p_265584_;
			p_265584_ = j;
		}

		if (p_265618_ > this.getX() + this.width) {
			p_265618_ = this.getX() + this.width;
		}

		if (p_265338_ > this.getX() + this.width) {
			p_265338_ = this.getX() + this.width;
		}

		p_281400_.fill(RenderType.guiTextHighlight(), p_265338_, p_265693_, p_265618_, p_265584_, -16776961);
	}

	public void setMaxLength(int p_94200_) {
		this.maxLength = p_94200_;
		if (this.value.length() > p_94200_) {
			this.value = this.value.substring(0, p_94200_);
			this.onValueChange(this.value);
		}

	}

	private int getMaxLength() {
		return this.maxLength;
	}

	public int getCursorPosition() {
		return this.cursorPos;
	}

	public boolean isBordered() {
		return this.bordered;
	}

	public void setBordered(boolean p_94183_) {
		this.bordered = p_94183_;
	}

	public void setTextColor(int p_94203_) {
		this.textColor = p_94203_;
	}

	public void setTextColorUneditable(int p_94206_) {
		this.textColorUneditable = p_94206_;
	}

	@Nullable
	public ComponentPath nextFocusPath(FocusNavigationEvent p_265216_) {
		return this.visible && this.isEditable ? super.nextFocusPath(p_265216_) : null;
	}

	public boolean isMouseOver(double p_94157_, double p_94158_) {
		return this.visible && p_94157_ >= (double)this.getX() && p_94157_ < (double)(this.getX() + this.width) && p_94158_ >= (double)this.getY() && p_94158_ < (double)(this.getY() + this.height);
	}

	public void setFocused(boolean p_265520_) {
		if (this.canLoseFocus || p_265520_) {
			super.setFocused(p_265520_);
			if (p_265520_) {
				this.focusedTime = Util.getMillis();
			}

		}
	}

	private boolean isEditable() {
		return this.isEditable;
	}

	public void setEditable(boolean p_94187_) {
		this.isEditable = p_94187_;
	}

	public int getInnerWidth() {
		return this.isBordered() ? this.width - 8 : this.width;
	}

	public void setHighlightPos(int p_94209_) {
		this.highlightPos = Mth.clamp(p_94209_, 0, this.value.length());
		this.scrollTo(this.highlightPos);
	}

	private void scrollTo(int p_299591_) {
		if (this.font != null) {
			this.displayPos = Math.min(this.displayPos, this.value.length());
			int i = this.getInnerWidth();
			String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), i);
			int j = s.length() + this.displayPos;
			if (p_299591_ == this.displayPos) {
				this.displayPos -= this.font.plainSubstrByWidth(this.value, i, true).length();
			}

			if (p_299591_ > j) {
				this.displayPos += p_299591_ - j;
			} else if (p_299591_ <= this.displayPos) {
				this.displayPos -= this.displayPos - p_299591_;
			}

			this.displayPos = Mth.clamp(this.displayPos, 0, this.value.length());
		}
	}

	public void setCanLoseFocus(boolean p_94191_) {
		this.canLoseFocus = p_94191_;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean p_94195_) {
		this.visible = p_94195_;
	}

	public void setSuggestion(@Nullable String p_94168_) {
		this.suggestion = p_94168_;
	}

	public int getScreenX(int p_94212_) {
		return p_94212_ > this.value.length() ? this.getX() : this.getX() + this.font.width(this.value.substring(0, p_94212_));
	}

	public void updateWidgetNarration(NarrationElementOutput p_259237_) {
		p_259237_.add(NarratedElementType.TITLE, this.createNarrationMessage());
	}

	public void setHint(Component p_259584_) {
		this.hint = p_259584_;
	}
}