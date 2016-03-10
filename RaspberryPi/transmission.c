#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>

void send_serial(unsigned char a, unsigned char b, int fp){
     // Write to the port
      int values = (b << 8)  | a; 
      int n = write(fp,&values,2);
      if (n < 0) {
        perror("Write failed - ");
      }
 }


int main(int argc, char** argv)
{
    int fd, fp, bytes;
    unsigned char data[3];

    const char *pDevice = "/dev/input/mice"; /*mouse0*/

    // Open Mouse
    fd = open(pDevice, O_RDWR);
    if(fd == -1)
    {
        printf("ERROR Opening %s\n", pDevice);
        return -1;
    }

    signed char x, y;
    unsigned char a, b;

   // Open the Port. We want read/write, no "controlling tty" status, and op$
      fp = open("/dev/ttyAMA0", O_RDWR | O_NOCTTY | O_NDELAY);
      if (fp == -1) {
        perror("open_port: Unable to open /dev/ttyAMA0 - ");
        return(-1);
      }

      // Turn off blocking for reads, use (fd, F_SETFL, FNDELAY) if you want th$
      fcntl(fp, F_SETFL, 0);
     struct termios termios_p;
     speed_t speed = B9600;
     cfsetospeed(&termios_p, speed);

    while(1)
    {
        // Read Mouse     
        bytes = read(fd, data, sizeof(data));

        if(bytes > 0)
        {
            x = data[1];
            y = data[2];
            x = x + 127;
	    y = y + 127;
	    a=x;
            b=y;
	    // send data
	    send_serial(a,b,fp);
            usleep(50000);
        }
        
    }
    close(fd);
    close(fp);
    return 0; 
}
