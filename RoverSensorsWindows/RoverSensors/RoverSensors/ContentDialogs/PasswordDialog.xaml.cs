using Windows.System;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Input;

// The Content Dialog item template is documented at https://go.microsoft.com/fwlink/?LinkId=234238

namespace RoverSensors.ContentDialogs
{
    public sealed partial class PasswordDialog : ContentDialog
    {
        public PasswordDialog()
        {
            this.InitializeComponent();
        }

        private void ContentDialog_PrimaryButtonClick(ContentDialog sender, ContentDialogButtonClickEventArgs args)
        {
            if (PasswordBox.Password.ToString() == "R0ver2018gr")
            {

            }
            else
            {
                args.Cancel = true;
                ErrorText.Visibility = Visibility.Visible;
            }
        }

        private void ContentDialog_SecondaryButtonClick(ContentDialog sender, ContentDialogButtonClickEventArgs args)
        {
        }

        private void PasswordBox_PasswordChanged(object sender, RoutedEventArgs e)
        {

            ErrorText.Visibility = Visibility.Collapsed;

        }

        private void PasswordBox_KeyDown(object sender, KeyRoutedEventArgs e)
        {

        }
    }
}
